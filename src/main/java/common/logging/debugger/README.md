"SmartyLogger" a component responsible of all jsmarty logging system.

SmartyLoggerProperties class should be initialized once at JSmarty startup.

SmartyLogger will queue logs under a specific indexed format in redis queue; a solution grouping logstash, elastic and kibana will handle these logs and display them in a managed interface.

Also SmartyLogger will print the message of any log in Java console.

SmartyLogger offers the option to add extra fields and values by calling the method .addField("key", "value") ....

"Component" model class contains constants that represent each component name - (components names should be added in this class) 

i.e: logger.INFO(Component.PHONETIC, "log message here").addField("field1", "value1").addField("field2", "value2").print();