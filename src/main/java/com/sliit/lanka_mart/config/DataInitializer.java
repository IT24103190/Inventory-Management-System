package com.sliit.lanka_mart.config;

import com.sliit.lanka_mart.model.*;
import com.sliit.lanka_mart.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if not exists
        if (roleRepository.count() == 0) {
            initializeRoles();
        }

        // Initialize sample user if not exists
        if (userRepository.count() == 0) {
            initializeSampleUsers();
        }

        // Initialize sample suppliers if not exists
        if (supplierRepository.count() == 0) {
            initializeSampleSuppliers();
        }

        // Initialize categories if not exists
        if (categoryRepository.count() == 0) {
            initializeCategories();
        }

        // Initialize sample products if not exists
        if (productRepository.count() == 0) {
            initializeSampleProducts();
        }

        // Initialize sample purchase orders if not exists
        if (purchaseOrderRepository.count() == 0) {
            initializeSamplePurchaseOrders();
        }
    }

    private void initializeRoles() {
        String[] roleNames = {
                "INVENTORY_MANAGER",
                "WAREHOUSE_SUPERVISOR",
                "SALES_EXECUTIVE",
                "ACCOUNTANT",
                "ADMIN"
        };

        for (String roleName : roleNames) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role);
        }
        System.out.println("Roles initialized successfully");
    }

    private void initializeSampleUsers() {
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow();
        Role inventoryRole = roleRepository.findByRoleName("INVENTORY_MANAGER").orElseThrow();
        Role salesRole = roleRepository.findByRoleName("SALES_EXECUTIVE").orElseThrow();

        // Admin user
        User admin = new User();
        admin.setUserId("USR00001");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@lankamart.lk");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setRole(adminRole);
        userRepository.save(admin);

        // Inventory Manager
        User inventoryManager = new User();
        inventoryManager.setUserId("USR00002");
        inventoryManager.setFirstName("John");
        inventoryManager.setLastName("Doe");
        inventoryManager.setEmail("john.doe@lankamart.lk");
        inventoryManager.setPasswordHash(passwordEncoder.encode("password123"));
        inventoryManager.setRole(inventoryRole);
        userRepository.save(inventoryManager);

        // Sales Executive
        User salesExecutive = new User();
        salesExecutive.setUserId("USR00003");
        salesExecutive.setFirstName("Jane");
        salesExecutive.setLastName("Smith");
        salesExecutive.setEmail("jane.smith@lankamart.lk");
        salesExecutive.setPasswordHash(passwordEncoder.encode("password123"));
        salesExecutive.setRole(salesRole);
        userRepository.save(salesExecutive);

        System.out.println("Sample users initialized successfully");
    }

    private void initializeSampleSuppliers() {
        // Supplier 1 - Electronics & Technology
        Supplier supplier1 = new Supplier();
        supplier1.setSupplierName("Tech Solutions Ltd");
        supplier1.setEmail("contact@techsolutions.lk");
        supplier1.setPhoneNumber("+94-11-234-5678");
        supplier1.setCompanyName("Tech Solutions Ltd");
        supplier1.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier1.setIsActive(true);
        supplierRepository.save(supplier1);

        // Supplier 2 - Electronics & Gadgets
        Supplier supplier2 = new Supplier();
        supplier2.setSupplierName("Global Electronics");
        supplier2.setEmail("orders@globalelectronics.lk");
        supplier2.setPhoneNumber("+94-11-345-6789");
        supplier2.setCompanyName("Global Electronics Pvt Ltd");
        supplier2.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier2.setIsActive(true);
        supplierRepository.save(supplier2);

        // Supplier 3 - Fashion & Clothing
        Supplier supplier3 = new Supplier();
        supplier3.setSupplierName("Fashion Forward");
        supplier3.setEmail("supply@fashionforward.lk");
        supplier3.setPhoneNumber("+94-11-456-7890");
        supplier3.setCompanyName("Fashion Forward Industries");
        supplier3.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier3.setIsActive(true);
        supplierRepository.save(supplier3);

        // Supplier 4 - Food & Beverages
        Supplier supplier4 = new Supplier();
        supplier4.setSupplierName("Ceylon Food Supplies");
        supplier4.setEmail("orders@ceylonfood.lk");
        supplier4.setPhoneNumber("+94-11-567-8901");
        supplier4.setCompanyName("Ceylon Food Supplies & Distribution");
        supplier4.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier4.setIsActive(true);
        supplierRepository.save(supplier4);

        // Supplier 5 - Home & Garden
        Supplier supplier5 = new Supplier();
        supplier5.setSupplierName("Green Thumb Garden");
        supplier5.setEmail("supply@greenthumb.lk");
        supplier5.setPhoneNumber("+94-11-678-9012");
        supplier5.setCompanyName("Green Thumb Garden Center");
        supplier5.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier5.setIsActive(true);
        supplierRepository.save(supplier5);

        // Supplier 6 - Sports & Outdoors
        Supplier supplier6 = new Supplier();
        supplier6.setSupplierName("SportsMax Equipment");
        supplier6.setEmail("orders@sportsmax.lk");
        supplier6.setPhoneNumber("+94-11-789-0123");
        supplier6.setCompanyName("SportsMax Equipment & Supplies");
        supplier6.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier6.setIsActive(true);
        supplierRepository.save(supplier6);

        // Supplier 7 - Books & Media
        Supplier supplier7 = new Supplier();
        supplier7.setSupplierName("BookWorld Publishers");
        supplier7.setEmail("supply@bookworld.lk");
        supplier7.setPhoneNumber("+94-11-890-1234");
        supplier7.setCompanyName("BookWorld Publishers & Distributors");
        supplier7.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier7.setIsActive(true);
        supplierRepository.save(supplier7);

        // Supplier 8 - Automotive Parts
        Supplier supplier8 = new Supplier();
        supplier8.setSupplierName("AutoParts Pro");
        supplier8.setEmail("orders@autopartspro.lk");
        supplier8.setPhoneNumber("+94-11-901-2345");
        supplier8.setCompanyName("AutoParts Pro Ltd");
        supplier8.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier8.setIsActive(true);
        supplierRepository.save(supplier8);

        // Supplier 9 - Health & Beauty
        Supplier supplier9 = new Supplier();
        supplier9.setSupplierName("HealthCare Plus");
        supplier9.setEmail("supply@healthcareplus.lk");
        supplier9.setPhoneNumber("+94-11-012-3456");
        supplier9.setCompanyName("HealthCare Plus Pharmaceuticals");
        supplier9.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier9.setIsActive(true);
        supplierRepository.save(supplier9);

        // Supplier 10 - Office Supplies
        Supplier supplier10 = new Supplier();
        supplier10.setSupplierName("Office Essentials");
        supplier10.setEmail("orders@officeessentials.lk");
        supplier10.setPhoneNumber("+94-11-123-4567");
        supplier10.setCompanyName("Office Essentials & Stationery");
        supplier10.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier10.setIsActive(true);
        supplierRepository.save(supplier10);

        // Supplier 11 - Inactive Supplier (for testing)
        Supplier supplier11 = new Supplier();
        supplier11.setSupplierName("Old Supplier Co");
        supplier11.setEmail("old@oldsupplier.lk");
        supplier11.setPhoneNumber("+94-11-999-9999");
        supplier11.setCompanyName("Old Supplier Company");
        supplier11.setPasswordHash(passwordEncoder.encode("supplier123"));
        supplier11.setIsActive(false); // Inactive supplier
        supplierRepository.save(supplier11);

        System.out.println("Sample suppliers initialized successfully - 11 suppliers created");
    }

    private void initializeCategories() {
        String[] categoryNames = {
                "Electronics",
                "Clothing",
                "Food & Beverages",
                "Home & Garden",
                "Sports & Outdoors",
                "Books & Media"
        };

        for (String categoryName : categoryNames) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
        System.out.println("Categories initialized successfully");
    }

    private void initializeSampleProducts() {
        Category electronics = categoryRepository.findByCategoryName("Electronics").orElseThrow();
        Category clothing = categoryRepository.findByCategoryName("Clothing").orElseThrow();

        // Sample Product 1
        Product product1 = new Product();
        product1.setProductId("PRD00001");
        product1.setProductName("Laptop Computer");
        product1.setDescription("High-performance laptop for business and personal use");
        product1.setCategory(electronics);
        product1.setQuantityInStock(50);
        product1.setReorderThreshold(10);
        product1.setUnitCost(new BigDecimal("45000.00"));
        product1.setUnitPrice(new BigDecimal("65000.00"));
        productRepository.save(product1);

        // Sample Product 2
        Product product2 = new Product();
        product2.setProductId("PRD00002");
        product2.setProductName("T-Shirt");
        product2.setDescription("Cotton t-shirt, various sizes and colors");
        product2.setCategory(clothing);
        product2.setQuantityInStock(200);
        product2.setReorderThreshold(50);
        product2.setUnitCost(new BigDecimal("500.00"));
        product2.setUnitPrice(new BigDecimal("1200.00"));
        productRepository.save(product2);

        // Sample Product 3 (Low Stock)
        Product product3 = new Product();
        product3.setProductId("PRD00003");
        product3.setProductName("Wireless Mouse");
        product3.setDescription("Ergonomic wireless mouse");
        product3.setCategory(electronics);
        product3.setQuantityInStock(8); // Below threshold
        product3.setReorderThreshold(15);
        product3.setUnitCost(new BigDecimal("1500.00"));
        product3.setUnitPrice(new BigDecimal("2500.00"));
        productRepository.save(product3);

        System.out.println("Sample products initialized successfully");
    }

    private void initializeSamplePurchaseOrders() {
        Supplier supplier = supplierRepository.findByEmail("contact@techsolutions.lk").orElseThrow();
        User inventoryManager = userRepository.findByEmail("john.doe@lankamart.lk").orElseThrow();

        // Get some products
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return;
        }

        // Purchase Order 1
        PurchaseOrder po1 = new PurchaseOrder();
        po1.setPurchaseOrderId("PO000001");
        po1.setOrderDate(LocalDateTime.now().minusDays(5));
        po1.setStatus("COMPLETED");
        po1.setSupplier(supplier);
        po1.setInventoryManager(inventoryManager);

        PurchaseOrderLine line1 = new PurchaseOrderLine();
        line1.setPurchaseOrder(po1);
        line1.setProduct(products.get(0));
        line1.setQuantityOrdered(50);

        po1.setOrderLines(new ArrayList<>());
        po1.getOrderLines().add(line1);
        purchaseOrderRepository.save(po1);

        // Purchase Order 2 - Pending
        PurchaseOrder po2 = new PurchaseOrder();
        po2.setPurchaseOrderId("PO000002");
        po2.setOrderDate(LocalDateTime.now().minusDays(2));
        po2.setStatus("PENDING");
        po2.setSupplier(supplier);
        po2.setInventoryManager(inventoryManager);

        if (products.size() > 1) {
            PurchaseOrderLine line2 = new PurchaseOrderLine();
            line2.setPurchaseOrder(po2);
            line2.setProduct(products.get(1));
            line2.setQuantityOrdered(100);

            po2.setOrderLines(new ArrayList<>());
            po2.getOrderLines().add(line2);
            purchaseOrderRepository.save(po2);
        }

        System.out.println("Sample purchase orders initialized successfully");
    }
}