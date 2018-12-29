package com.android.ctyon.copyhome.adapter.data;

public class AppClassName {
    private  String name;
    private String packageName;
    private String className;

    public AppClassName(){

    }

    public AppClassName(String name, String packageName, String className){
        this.name = name;
        this.packageName = packageName;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }
}
