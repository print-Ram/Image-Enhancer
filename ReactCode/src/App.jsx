import React, { useState } from "react";
import axios from "axios";

function App() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [outputImage, setOutputImage] = useState(null);

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
    setOutputImage(null); // reset result on file change
  };

  const handleAction = async (action) => {
    if (!selectedFile) return;

    const formData = new FormData();
    formData.append("file", selectedFile);

    const endpoint =
      action === "enhance"
        ? "http://localhost:8080/api/image/upload"
        : "http://localhost:8080/api/image/cartoon";

    try {
      const response = await axios.post(endpoint, formData, {
        responseType: "blob",
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      const imageURL = URL.createObjectURL(response.data);
      setOutputImage(imageURL);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div style={{ textAlign: "center", padding: "20px" }}>
      <h1>Image Enhancer & Cartoonifier</h1>

      <input type="file" onChange={handleFileChange} />
      <br /><br />

      <button onClick={() => handleAction("enhance")}>Enhance</button>
      <button onClick={() => handleAction("cartoon")} style={{ marginLeft: "10px" }}>
        Cartoonify
      </button>

      {outputImage && (
        <>
          <h3>Result:</h3>
          <img src={outputImage} alt="Result" style={{ maxWidth: "500px", marginTop: "20px" }} />
        </>
      )}
    </div>
  );
}

export default App;
