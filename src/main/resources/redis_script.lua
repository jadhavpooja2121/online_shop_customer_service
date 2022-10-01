local name ='name';
local value = ARGV[1];
-- redis.call('HGET', name, KEYS[1]);
-- redis.call('HSET', name, KEYS[1], value)
redis.call('HSET', name,"official", value)
