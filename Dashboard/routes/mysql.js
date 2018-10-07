var mysql=require('mysql');
var connection= mysql.createConnection({
    host: "35.229.191.161",
    user: "root",         
    password: "guswls45", 
    database:"test" 
   });

module.exports=connection;