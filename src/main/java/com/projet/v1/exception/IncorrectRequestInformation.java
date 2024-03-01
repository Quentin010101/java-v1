package com.projet.v1.exception;

public class IncorrectRequestInformation extends Exception{
    public IncorrectRequestInformation(String errorMessage){
        super(errorMessage);
    }
}
