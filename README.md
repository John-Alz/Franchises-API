# Franchises API Challenge Solution 🚀

This project is the solution for the Franchises API challenge. It is a reactive API built with Spring WebFlux to manage a network of franchises, their branches, and product inventory, following **Hexagonal Architecture** principles and deployed on AWS using Terraform.

## Features ✨

* **Franchise Management:** Create and update franchise information.
* **Branch Management:** Add new branches to existing franchises and update their names.
* **Product Management:** Add products, delete them, and update their names and stock levels.
* **Reactive Endpoints:** All APIs are non-blocking, built with Spring WebFlux using `RouterFunctions` and `Handlers`.
* **Reporting:** An endpoint to get the product with the highest stock for each branch of a given franchise.
* **API Documentation:** All endpoints are documented via Swagger/OpenAPI.
* **Containerization:** The application is fully containerized.
* **Infrastructure as Code:** The entire cloud infrastructure is defined and managed using Terraform, organized into reusable modules.

---

## Tech Stack & Architecture 🛠️

* **Framework:** Spring Boot 3 / Spring WebFlux
* **Language:** Java 17
* **Build Tool:** Gradle
* **Database:**
    * **Local:** H2 (In-memory via Spring Boot auto-configuration)
    * **Cloud:** AWS RDS (MySQL)
* **Persistence:** R2DBC for reactive database access.
* **API Style:** Functional Reactive Endpoints (`RouterFunctions` & `Handlers`)
* **Documentation:** OpenAPI 3 (Springdoc)
* **Deployment:** Podman/Docker, Terraform, AWS (ECS Fargate, RDS, ALB, ECR)

---

## Architecture: Hexagonal Architecture (Ports & Adapters)

The project is structured following **Hexagonal Architecture**, also known as the **Ports and Adapters** pattern. This design creates a strong separation between the core business logic (the "inside" of the hexagon) and external concerns like the web framework, database, and other services (the "outside").

Communication happens through well-defined **Ports** (interfaces owned by the domain) and their corresponding **Adapters** (implementations in the infrastructure layer). This makes the core domain completely independent of any external technology.

* **`domain`**: Contains the core business logic, models (entities), and **Ports** (interfaces). This is the "inside" of the hexagon and has zero dependencies on Spring or any other external framework.
    * `model`: The business entities and logic.
    * `usecase`: Implements the business rules and orchestrates the domain logic.
    * `api` (Input Ports): Defines the service interfaces that the application exposes to the outside world.
    * `spi` (Output Ports): Defines the interfaces required for persistence, which the domain needs to function.

* **`infrastructure`**: The "outside" world. This layer provides the concrete implementations (**Adapters**) for the ports defined in the domain.
    * `entry-points` (Driving Adapters): Implements the input ports, exposing the API using `RouterFunctions` and `Handlers`.
    * `adapters` (Driven Adapters): Implements the output ports, handling communication with the database using R2DBC.

* **`application`**: Contains the global bean configurations (`@Bean`) that assemble the application.

## Getting Started

### 1. Local Development 💻

**Prerequisites:**
* Java 17+
* Gradle 8+

**Running the Application:**

1.  Clone the repository:
    ```bash
    git clone <repository-url>
    cd franchises
    ```
2.  Build and run the application:
    ```bash
    ./gradlew bootRun
    ```
    The project uses an H2 in-memory database by default (via Spring Boot auto-configuration). The application will be available at `http://localhost:8080`.

### 2. Cloud Deployment with Terraform 🏗️

This section describes how to deploy the entire application and its infrastructure to AWS.

**Prerequisites:**
* **AWS CLI** configured with your credentials.
* **Terraform** installed.
* **Podman** or **Docker** installed.

**Deployment Steps:**

**Step 1: Build and Push the Container Image**

The application must be built and pushed to the AWS Elastic Container Registry (ECR) before the infrastructure can be deployed.

> **Note for Mac M-series (Apple Silicon) users:** You must build the image for the `linux/amd64` platform to ensure compatibility with AWS Fargate.

1.  Build the image (use the appropriate command for your system):
    ```bash
    # For Mac M1/M2/M3/M4
    podman build --platform linux/amd64 -t franchise-api .

    # For Intel/AMD systems
    docker build -t franchise-api .
    ```

2.  Authenticate with ECR and push the image. The exact commands can be found in the AWS ECR console for your repository after it's created in the next step. The process will look like this:
    ```bash
    # Login to AWS ECR
    aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <your-aws-account-id>.dkr.ecr.us-east-1.amazonaws.com

    # Tag the image
    docker tag franchise-api:latest <your-ecr-repository-url>:latest

    # Push the image
    docker push <your-ecr-repository-url>:latest
    ```

**Step 2: Deploy the Infrastructure**

1.  Navigate to the infrastructure directory:
    ```bash
    cd infra****
    ```
2.  Initialize Terraform:
    ```bash
    terraform init
    ```
3.  Apply the infrastructure plan. This command will provision the VPC, RDS database, ALB, ECS service, and all other necessary resources.
    ```bash
    terraform apply -var-file="dev.tfvars"
    ```
    Confirm the action by typing `yes`. The process will take several minutes.

**Step 3: Accessing the Deployed API**

Once the `apply` command is complete, Terraform will output the public URL of the application.
* **`alb_dns_name`**: This is the base URL for your API.

Use this URL in Postman or your browser to interact with the deployed application.

**Step 4: Cleaning Up**

To avoid ongoing charges, destroy all the created infrastructure when you are finished.
```bash
terraform destroy -var-file="dev.tfvars"