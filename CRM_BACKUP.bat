@ECHO ON

set TIMESTAMP=%DATE:~10,4%%DATE:~4,2%%DATE:~7,2%

REM Export all databases into file D:\path\backup\databases.[year][month][day].sql
"C:\xampp\mysql\bin\mysqldump.exe" -u crm -pcrm123!@# bits_crm > "D:\MySQLBackups\backupfiles\CRMBKUP-%TIMESTAMP%.sql"
