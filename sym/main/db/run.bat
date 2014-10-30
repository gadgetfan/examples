echo off
rem %1-host, %2-db name, %3-user, %4-pass, %5-sql-instruction
mysql -u  %3 -p%4 -h %1 -P 3307 %2 -e %5
rem pause