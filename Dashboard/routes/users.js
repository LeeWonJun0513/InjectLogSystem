var express = require('express');
var router = express.Router();
var fs=require('fs');
var mysql=require('mysql');
var connection=mysql.createConnection({
  host:'35.229.191.161',
  user:'root',
  password:'guswls45',
  database: 'test'
});
connection.connect();

/* GET users listing. */
router.get('/', function(req, res, next) {
  //res.locals.connection.query('SELECT count(*) as x,hour(Date) as y from log5 where day(Date)=day(now()) group by hour(Date);',function(error,results,fields){
  
  connection.query('SELECT hour(Date) as x,count(*) as y from log5 where day(Date)=day(now()) group by hour(Date);',function(err,results,fields)
  {
    if(err) throw err;
    console.log(JSON.stringify(results));
    res.send(JSON.stringify(results));
  });
  });
  //console.log('go to react');
  
 /* res.json([
    { x: 1, y: 10 }, { x: 2, y: 3 }, { x: 3, y: 5 }, { x: 4, y: 4 }, { x: 6, y: 7 },
    { x: 7, y: 2 }, { x: 8, y: 3 }, { x: 11, y: 2 }, { x: 12, y: 3 },
    { x: 13, y: 5 }, { x: 14, y: 4 }, { x: 16, y: 7 }, { x: 17, y: 2 }
]
);*/


module.exports = router;