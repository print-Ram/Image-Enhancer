# Image Enhancer Web Application

This project is a full-stack web application that allows users to upload an image and apply enhancements or cartoon effects.
It uses a React frontend, a Spring Boot backend, and a Python Flask API for image processing.

## Technologies Used

- React (Frontend)
- Spring Boot (Backend API)
- Python Flask (Image processing service)
- REST APIs for communication
- Axios (React API calls)
- OpenCV / PIL (Python image libraries)

## Architecture Overview

- **React Frontend**: Users upload images and choose "Enhance" or "Cartoonify".
- **Spring Boot Backend**: Receives the uploaded image, forwards it to the Flask server, and sends the processed image back to React.
- **Flask Server**: Applies enhancement or cartoonification and returns the processed image.

## Project Structure

```
/frontend (React App)
/backend (Spring Boot App)
/flask-api (Python Flask App)
```

## How to Run the Project

### 1. Flask API Setup

```bash
cd flask-api
pip install -r requirements.txt
python app.py
```

> Runs on `http://localhost:5000`

### 2. Spring Boot Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

> Runs on `http://localhost:8080`

(Spring Boot internally calls Flask.)

### 3. React Frontend Setup

```bash
cd frontend
npm install
npm start
```

> Runs on `http://localhost:3000`

## Features

- Upload an image
- Choose Enhancement Type:
  - Enhance (improve quality)
  - Cartoonify (apply cartoon effect)
- Download the processed image
- Clean and responsive UI

## API Endpoints

**React to Spring Boot:**

- `POST /api/image/upload`
- Payload: Multipart File + Choice (Enhance/Cartoonify)

**Spring Boot to Flask:**

- `POST /process`
- Payload: Image + Option
- Response: Processed Image

## Notes

- Ensure Flask server is running before Spring Boot server.
- Ports should be different for Spring Boot and Flask.
- CORS must be enabled.

##

