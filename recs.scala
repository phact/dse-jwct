//# Full Scan:
spark.sql("SELECT key_id, SUBSTRING(time_part, 0, 8), count(time_part, 0, 8) FROM jwct.events GROUP BY key_id, SUBSTRING(time_part, 0, 8)").show


//# Partial Scan:
case class EventId(key_id: Int, time_part: BigInt) // Defines partition key
case class Meta(data_key: String, data_source: String, key_id: Int, meta_data: String, target_table: Int, myType: Int) // Defines table

sc.cassandraTable[Meta]("jwct","meta")

var keysRDD =sc.cassandraTable("jwct","meta").map(row => row.getInt(2))

//Note: This section assumes that time_part is in this form: YYYYMMDDHHMM
keysRDD.flatMap(key => (0 to 5).flatMap(min => (0 to 23).map(hr => EventId(key, 201701010000+min*10+hr*100)))).first
val eventsRDD = keys.flatMap(key => (0 to 5).flatMap(min => (0 to 24).map(hr => EveOntId(key, BigInt("201701010000") + (min*10)+(hr*10)))))

resultRDD.map(row => ((row.get[BigInt]("key_id"),row.get[String]("time_part").substring(0,8)),1)).countByKey().take(5)
