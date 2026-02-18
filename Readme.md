# KCB Spring Boot Masking Starter Assessment

## Overview

This project implements a **reusable Spring Boot Starter** for masking sensitive data in logs, along with a **sample Books API application** that demonstrates its usage.  
It is designed to mask sensitive fields like emails, phone numbers, SSNs, and credit card numbers without modifying the original objects, supporting nested objects and lists, and following **SOLID principles**.

The project contains two modules:

- `p11-masking-spring-boot-starter` – Reusable masking starter library
- `books-api-demo` – Sample consumer application using the starter

---

## Architecture Explanation

