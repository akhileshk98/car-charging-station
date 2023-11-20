package com.chargingstation;

import static java.nio.file.StandardOpenOption.APPEND;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Car {
boolean chargeState;
int id;
//Constructor
Car()
{
	this.chargeState = false;
	this.id = 0;
	
}
//Constructor for passing the id
Car(int id)
{
	this.chargeState = false;
	this.id = id;
	
}
//setter
void setid(int id)
{
	this.id = id;
}
//getter
boolean getchargeState()
{
	return this.chargeState;
}
int getid()
{
	return this.id;
}
}