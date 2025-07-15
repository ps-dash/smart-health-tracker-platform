1. Open command line at user-service directory
2. run psql -d postgres => It'll take you to the postgres prompt
3. copy paste the content of dbScripts/create_db_user.sql and run
4. Type \q to quit
5. Run psql -d user_service_db -U user_service_app -f dbScripts/db_setup.sql
6. Run psql -d user_service_db -U user_service_app -f dbScripts/schema_data.sql