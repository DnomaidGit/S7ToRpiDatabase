***
# S7ToRpiDatabase
***
## Table of Contents
1. [General Info](#general-info)
2. [Development Environment](#development-environment)
3. [Tested](#tested)
4. [Architecture](#architecture)
5. [Tutorial](#tutorial)
***
## General Info
Project to store data Plc using Mysql database.
***
## Development Environment
* Netbeans 8.1
* Step 7 5.5
* Wincc Flexible 2008 SP3
* Mysql workbench
***
## Tested
* Raspberry pi 2
* Plc S7 300
* Pc Windows 7
***
## Architecture
![Architecture](Images/arch.gif)
***
## Package

|  | COMMENT |
| ------ | ------ |
| PLC | Plc + Hmi project.|
| S7_HIBERNATE_01 |  Java desktop project with Hibernate. Connection to Plc with Libnodave. Mysql database. |
| Web_dnomaid | Java web project with Glassfish server. Mysql database. |

***
## Tutorial
Watch the video on youtube.

[![Watch the video](https://img.youtube.com/vi/paJqPzNHhUM/0.jpg)](https://www.youtube.com/watch?v=paJqPzNHhUM)
***


