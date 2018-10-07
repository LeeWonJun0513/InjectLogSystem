var connection=require('./mysql');

var fs=require('fs');

connection.connect();
var file='C:/react-backend/cloudlogapp/src/views/LogTableList/table.json';
connection.query('select*from log5;',function(err,results,fields)
{
    if(err) throw err;
    console.log(results.length);
    fs.writeFileSync(file,'[',function(error){});
    for(var i=0;i<results.length;i++){
        fs.appendFileSync(file,'[',function(err){});
        fs.appendFileSync(file,JSON.stringify(results[i].Date).concat(','),function(err){});
        fs.appendFileSync(file,JSON.stringify(results[i].Thread).concat(','),function(err){});
        fs.appendFileSync(file,JSON.stringify(results[i].Priority).concat(','),function(err){});
        fs.appendFileSync(file,JSON.stringify(results[i].Class).concat(','),function(err){});
        fs.appendFileSync(file,JSON.stringify(results[i].Method).concat(','),function(err){});
        if(i==results.length-1)
        fs.appendFileSync(file,JSON.stringify(results[i].Query).concat(']]'),function(err){});
        else
        fs.appendFileSync(file,JSON.stringify(results[i].Query).concat('],'),function(err){});
    }
connection.end();
})