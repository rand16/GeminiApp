package com.example.geminiapp

import androidx.appfunctions.AppFunction
import androidx.appfunctions.AppFunctionContext
import androidx.appfunctions.AppFunctionService
import androidx.appfunctions.AppFunctionServiceEntryPoint

@AppFunctionServiceEntryPoint(
    serviceName = "MyGeneratedAppFunctionService",
    appFunctionXmlFileName = "my_app_functions"
)
abstract class MyAppFunctionService : AppFunctionService() {

    @AppFunction
    suspend fun greet(name: String): String {
        return "Hello, $name! Executed via AppFunction Service."
    }
}
