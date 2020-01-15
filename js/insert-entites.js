//eyJhbGciOiJIUzI1NiJ9.eyJpZHVzZXIiOjEsImlzYWRtaW4iOnRydWUsImlhdCI6MTU3ODI0NzUwNiwic3ViIjoiT2J5a2EiLCJleHAiOjE2MDk4MDQ0NTh9.aPM5LdueEU_oYvI33Xn5aOP-p5L3m3bvjjH7v-xu4Rs

var mysql = require('mysql');

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: "project2-amt-pokemon",
  insecureAuth : true
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
  
  var sql = "DELETE FROM capture_entity";
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("Number of records deleted: " + result.affectedRows);
  });
  
  var sql = "DELETE FROM pokemon_entity";
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("Number of records deleted: " + result.affectedRows);
  });
  
  var sql = "DELETE FROM trainer_entity";
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("Number of records deleted: " + result.affectedRows);
  });
  
  
  
  var sql = "INSERT INTO pokemon_entity(poke_dex_id, category, height, hp, id_user, name, type) VALUES ?";
  var values = [];
  for(var i = 0; i < 1000000; ++i){
        values.push([i+1, 'category', '150', 12, 1, "Pokemon name", "Pokemon type"]);
  }
  con.query(sql, [values], function (err, result) {
    if (err) throw err;
    console.log("Number of records inserted: " + result.affectedRows);
  });
  
  var sql = "INSERT INTO trainer_entity(trainer_id, age, gender, id_user, name, number_of_badges, surname) VALUES ?";
  var values = [];
  for(var i = 0; i < 1000; ++i){
        values.push([i+1,12, 'homme', 1, "Dresseur", 8, "Jordan"]);
  }
  con.query(sql, [values], function (err, result) {
    if (err) throw err;
    console.log("Number of records inserted: " + result.affectedRows);
  });
  
  /*var sql = "INSERT INTO capture_entity(id, date_capture, id_user, id_pokemon, id_trainer) VALUES ?";
  var values = [];
  for(var i = 1; i <= 1000; ++i){
      for(var j = 1; j <= 1000; ++j){
        values.push([(i*1000)+j, 'aujourdhui', 1, i, j]);
    }
  }
  con.query(sql, [values], function (err, result) {
    if (err) throw err;
    console.log("Number of records inserted: " + result.affectedRows);
  }); */
});
