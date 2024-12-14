# Task-Manager

A simple task management application that lets users add, view, and remove tasks with details like name, description, date, and time, stored in a JSON file for persistence.

## How To Use

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/shlok-nahar/Task-Manager.git
cd Task-Manager
```

### 2. Build the Project

```bash
javac -cp lib/json-20240303.jar -d out src/*.java
```

### 3. Running the Application

```bash
java -cp out:lib/json-20240303.jar TaskManager
```
