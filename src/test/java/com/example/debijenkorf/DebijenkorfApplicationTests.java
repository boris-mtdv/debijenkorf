package com.example.debijenkorf;

import com.example.debijenkorf.client.AmazonBucketClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DebijenkorfApplicationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private AmazonBucketClient amazon_bucket_client;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void test_flush_original() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/image/flush/original/dummy_seo_name/?reference=test.jpg", String.class);
		HashMap<String, HashMap<String, byte[]>> image_map = (HashMap<String, HashMap<String, byte[]>>) amazon_bucket_client.get_image_map();

		boolean image_gone = true;
		for (Object entry : image_map.keySet()) {
			HashMap<String, byte[]> sub_map = (HashMap<String, byte[]>) image_map.get((String) entry);
			if (sub_map.containsKey("missing.jpg")) {
				image_gone = false;
			}
		}
		assert response.contains("image removed");
		assert image_gone;
	}

	@Test
	void test_flush_thumbnail() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/image/flush/thumbnail/dummy_seo_name/?reference=test.jpg", String.class);
		HashMap<String, HashMap<String, byte[]>> image_map = (HashMap<String, HashMap<String, byte[]>>) amazon_bucket_client.get_image_map();
		HashMap<String, byte[]> sub_map = (HashMap<String, byte[]>) image_map.get("thumbnail");

		assert response.contains("image removed");
		assert!sub_map.containsKey("test.jpg");
		}

	@Test
	void test_show_new_thumbnail() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/image/show/thumbnail/dummy_seo_name/?reference=new.jpg", String.class);
		HashMap<String, HashMap<String, byte[]>> image_map = (HashMap<String, HashMap<String, byte[]>>) amazon_bucket_client.get_image_map();
		HashMap<String, byte[]> sub_map = (HashMap<String, byte[]>) image_map.get("thumbnail");

		assert response.contains("content_retrieved");
		assert sub_map.containsKey("new.jpg");
	}


	}
