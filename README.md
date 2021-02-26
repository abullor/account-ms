# account-ms

This service is responsible for account related operations. It has 4 actions available.

# Create an account

curl -X POST HOST:PORT/accounts -H 'Content-type:application/json' -d '{"name": "aname", "password": "apassword", "email": "newmail@gmail.com", "phone": "+1 (212) 555-3456", "notificationPreference": "MAIL"}'

# Update an account

curl -X PUT HOST:PORT/accounts/{accountId} -H 'Content-type:application/json' -d '{"email": "updated@gmail.com"}'


# Login

curl -X POST HOST:PORT/login -H 'Content-type:application/json' -d '{"name": "aname", "password": "apassword"}'

# Retrieve an account

curl -X GET HOS:PORT/accounts/{accountId} -H 'Content-type:application/json'
