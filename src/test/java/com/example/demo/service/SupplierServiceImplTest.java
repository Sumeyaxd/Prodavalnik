package com.example.demo.service;

import com.example.demo.model.dto.AddSupplierDTO;
import com.example.demo.model.dto.SupplierDTO;
import com.example.demo.service.impl.SupplierServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SupplierServiceImplTest {
    private MockWebServer mockWebServer;

    @Autowired
    private SupplierService supplierService;

    @BeforeEach
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8080);
    }

    @AfterEach
    public void tearUp() throws IOException {
        mockWebServer.shutdown();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testAddSupplier() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        AddSupplierDTO addSupplierDTO = new AddSupplierDTO();

        supplierService.addSupplier(addSupplierDTO);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/suppliers", request.getPath());
        assertEquals("POST", request.getMethod());
    }

    @Test
    public void testDeleteSupplier() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        long supplierId = 1L;
        supplierService.deleteSupplier(supplierId);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/suppliers/1", request.getPath());
        assertEquals("DELETE", request.getMethod());
    }

    @Test
    public void testGetAllSuppliers() throws InterruptedException {
        String responseBody = "[{\"id\":1,\"name\":\"Supplier1\"}, {\"id\":2,\"name\":\"Supplier2\"}]";

        mockWebServer.enqueue(new MockResponse()
                .setBody(responseBody)
                .addHeader("Content-Type", "application/json"));

        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/suppliers", request.getPath());
        assertEquals("GET", request.getMethod());

        assertNotNull(suppliers);
        assertEquals(2, suppliers.size());
        assertEquals(1L, suppliers.get(0).getId());
        assertEquals("Supplier1", suppliers.get(0).getName());
        assertEquals(2L, suppliers.get(1).getId());
        assertEquals("Supplier2", suppliers.get(1).getName());
    }
}
