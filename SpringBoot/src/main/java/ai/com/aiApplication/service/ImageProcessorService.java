package ai.com.aiApplication.service;
import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageProcessorService {

    private final String AI_SERVICE_URL = "http://localhost:5000/enhance"; // or /cartoon

    public ResponseEntity<byte[]> enhanceImage(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Wrap file into a resource
        ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                "http://localhost:5000/enhance",  // or /cartoon if you're calling cartoonify
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        return ResponseEntity
                .status(response.getStatusCode())
                .contentType(MediaType.IMAGE_PNG)
                .body(response.getBody());
    }
    //Cartoon Method
    public ResponseEntity<byte[]> cartoonifyImage(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                "http://localhost:5000/cartoon",  // 👈 cartoon endpoint
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        return ResponseEntity
                .status(response.getStatusCode())
                .contentType(MediaType.IMAGE_PNG)
                .body(response.getBody());
    }

}
