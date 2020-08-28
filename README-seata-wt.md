# 问题处理1

存在大量事务，经确认，trx_mysql_thread_id=0 的事务全部为XA事务。
因为trx_mysql_thread_id=0 的事务无法通过kill trx_mysql_thread_id 的方式处理，所以，需要回滚这些XA事务。

查看XA事务信息

mysql> XA RECOVER;
+----------+--------------+--------------+-----------------------------------------------------+
| formatID | gtrid_length | bqual_length | data                                                |
+----------+--------------+--------------+-----------------------------------------------------+
|     9752 |           33 |           18 | 172.17.0.5:8091:41940156638318592-41940157280047104 |
|     9752 |           33 |           18 | 172.17.0.5:8091:41940156638318592-41940159746297856 |
+----------+--------------+--------------+-----------------------------------------------------+
2 rows in set (0.00 sec)

 XA事务回滚命令的格式：xa rollback 'left(data,gtrid_length)'，'substr(data,gtrid_length+1,bqual_length)', formatID;

 以上查出来的信息拼接结果为（以下举其中一个为例）
 
 mysql> select concat('xa rollback \'',left('172.17.0.5:8091:41940156638318592-41940157280047104',33),'\' , \''     ,substr('172.17.0.5:8091:41940156638318592-41940157280047104',33+1,18),'\' , ',9752) jb ; 
 +-------------------------------------------------------------------------------+
 | jb                                                                            |
 +-------------------------------------------------------------------------------+
 | xa rollback '172.17.0.5:8091:41940156638318592' , '-41940157280047104' , 9752 |
 +-------------------------------------------------------------------------------+
 1 row in set (0.00 sec)
 
mysql> xa rollback '172.17.0.5:8091:41940156638318592' , '-41940157280047104' , 9752;
Query OK, 0 rows affected (0.00 sec)



# XAER_RMFAIL: The command cannot be executed when global transaction is in the  IDLE state

2020-08-27 16:42:35.807  INFO 6983 --- [_RMROLE_1_10_16] Branch Rollbacked result: PhaseTwo_RollbackFailed_Retryable
2020-08-27 16:42:36.724  INFO 6983 --- [_RMROLE_1_11_16] rm handle branch rollback process:xid=172.17.0.5:8091:42287542946516992,branchId=42287545605705728,branchType=XA,resourceId=jdbc:mysql://172.81.203.33:3306/db_seata,applicationData=null
2020-08-27 16:42:36.724  INFO 6983 --- [_RMROLE_1_11_16] Branch Rollbacking: 172.17.0.5:8091:42287542946516992 42287545605705728 jdbc:mysql://172.81.203.33:3306/db_seata
2020-08-27 16:42:36.903  INFO 6983 --- [_RMROLE_1_11_16] 172.17.0.5:8091:42287542946516992-42287545605705728 rollback failed since XAER_RMFAIL: The command cannot be executed when global transaction is in the  IDLE state

正在执行中的事务被其他事务干扰了，是spring的事务干扰了，

增加noRollbackFor 配置，不让spring事务管理参与回滚操作
```
@Transactional(noRollbackFor=RuntimeException.class)
    public void reduce(String userId, int money) {
        String xid = RootContext.getXID();

        LOGGER.info("reduce account balance in transaction: " + xid);
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        int balance = jdbcTemplate.queryForObject("select money from account_tbl where user_id = ?", new Object[] {userId}, Integer.class);
        LOGGER.info("balance after transaction: " + balance);
        if (balance < 0) {
            throw new RuntimeException("Not Enough Money ...");
        }
    }
```
