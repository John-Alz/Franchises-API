variable "aws_region" {
  description = "The AWS region where resources will be created."
  type = string
  default = "us-east-1"
}

variable "project_name" {
  description = "A common name to prefix all resources."
  type        = string
  default     = "franchise"
}

variable "db_name" {
  description = "MySQL rds database name"
  type = string
  default = "franchises"
}

variable "db_username" {
  description = "The username for the RDS database"
  type = string
  default = "admin"
}

variable "db_password" {
  description = "The password for the RDS database."
  type        = string
  sensitive   = true
}

variable "db_instance_class" {
  description = "The instance type for the RDS database."
  type        = string
  default     = "db.t3.micro"
}

variable "ecs_task_cpu" {
  description = "The CPU units for the ECS task."
  type        = string
  default     = "256"
}

variable "ecs_task_memory" {
  description = "The memory for the ECS task."
  type        = string
  default     = "512"
}