# InvoiceSystem
This is an invoice system for freelancer to manage their billing.

This project is based on Docker+Java+MySql+React

[![Build Status](https://travis-ci.com/HurinHu/InvoiceSystem.svg)](https://travis-ci.com/HurinHu/InvoiceSystem)
[![codecov](https://codecov.io/gh/HurinHu/InvoiceSystem/branch/development/graph/badge.svg)](https://codecov.io/gh/HurinHu/InvoiceSystem)
![GitHub issues](https://img.shields.io/github/issues-raw/HurinHu/InvoiceSystem)
![GitHub](https://img.shields.io/github/license/HurinHu/InvoiceSystem)

## Environment

### Backend

* Docker: 19.03.8
* MySQL: 8.0.20
* Java: 1.8.0
* Gradle: 6.3
* Spring Boot: 2.3.1

Default:
* Port: 8080(manual run)/9090(docker)
* Base URL: /

Setting:
* Docker: `.env`
* Backend: `src\com\iceloof\Setting.java`
* Frontend router: `src\com\iceloof\controller\RouterController.java`

### Frontend

Package version:
* react: 16.13.1
* typescript: 3.9.6
* react-redux: 7.2.0
* redux: 4.0.5
* redux-mock-store: 1.5.4
* redux-persist: 6.0.0
* redux-thunk: 2.3.0
* typescript: 3.9.6

Setting:
* Change API proxy to backend url at `frontend/package.json`

Default:
* Port: 3000
* Base URL: /

## Run / Compile / Build

### Backend

Start development mode: `./dev backend`

Build: `cd backend & gradle build`

Manual run: `cd backend & gradle bootRun`

Created db folder with permission: `./dev` file line 10 (replace user:user with your current user name)

### Frontend

Install dependencies:  `yarn add`

Start development mode: `./dev frontend` or `cd frontend & yarn start`

Manual build: `yarn build`

### Build War

Build: `./prod`

Output file: `./build/InvoiceSystem.war`
