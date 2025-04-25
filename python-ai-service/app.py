# main.py
import io

import numpy as np
import cv2
from fastapi import FastAPI, File, UploadFile, Response
from fastapi.responses import StreamingResponse
from PIL import Image, ImageEnhance

app = FastAPI()


@app.post("/enhance")
async def enhance_image(file: UploadFile = File(...)):
    image = Image.open(io.BytesIO(await file.read()))
    enhanced = ImageEnhance.Contrast(image).enhance(2.0)
    buf = io.BytesIO()
    enhanced.save(buf, format="PNG")
    buf.seek(0)
    return StreamingResponse(buf, media_type="image/png")


@app.post("/cartoon")
async def cartoonify(file: UploadFile = File(...)):
    # read raw bytes & turn into OpenCV image
    contents = await file.read()
    arr = np.frombuffer(contents, np.uint8)
    image = cv2.imdecode(arr, cv2.IMREAD_COLOR)

    # 1) detect edges
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    gray = cv2.medianBlur(gray, 5)
    edges = cv2.adaptiveThreshold(
        gray, 255,
        cv2.ADAPTIVE_THRESH_MEAN_C,
        cv2.THRESH_BINARY,
        blockSize=9,
        C=9
    )

    # 2) smooth color regions
    color = cv2.bilateralFilter(image, d=9, sigmaColor=250, sigmaSpace=250)

    # 3) combine
    cartoon = cv2.bitwise_and(color, color, mask=edges)

    # encode back to PNG
    success, encoded = cv2.imencode(".png", cartoon)
    if not success:
        return Response(status_code=500, content="Could not encode image")

    # wrap in StreamingResponse
    return StreamingResponse(io.BytesIO(encoded.tobytes()), media_type="image/png")
