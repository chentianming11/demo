var Date = Java.type('java.util.Date');
var date = new Date();
date.year += 1900;
print(date.year);  // 2014

var o1 = {};
var o2 = { foo: 'bar'};

Object.bindProperties(o1, o2);

print(o1.foo);    // bar
o1.foo = 'BAM';
print(o2.foo);    // BAM

print("   hehe".trimLeft());            // hehe
print("hehe    ".trimRight() + "he");   // hehehe

print(__FILE__, __LINE__, __DIR__);

var imports = new JavaImporter(java.io, java.lang);
with (imports) {
    var file = new File(__FILE__);
    System.out.println(file.getAbsolutePath());
    // /path/to/my/script.js
}

var list = new java.util.ArrayList();
list.add("s1");
list.add("s2");
list.add("s3");

var jsArray = Java.from(list);
print(jsArray);                                  // s1,s2,s3
print(Object.prototype.toString.call(jsArray));  // [object Array]