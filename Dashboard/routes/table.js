
var express=require('express');
var router= express.Router();
var mysql=require('mysql');
var connection=mysql.createConnection({
    host:'35.229.191.161',
    user:'root',
    password:'guswls45',
    database:'test'
});
  connection.connect();
  console.log('hello');
  router.get('/',function(req,res,next){

   connection.query('select *from log5',function(err,results,fields){
 if(err) throw err;
 console.log(results[0].Date);
  var map=new Array();
for(var i=0;i<results.length;i++)
   map[i]=new Array();
 for(var y=0;y<results.length;y++){
     map[y][0]=results[y].Date;
     map[y][1]=results[y].Thread;
     map[y][2]=results[y].Priority;
     map[y][3]=results[y].Class;
     map[y][4]=results[y].Method;
     map[y][5]=results[y].Query;
 }
 res.send(map);
});
});

module.exports=router;
