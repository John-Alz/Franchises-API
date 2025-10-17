variable "project_name" {
  description = "The name of the project."
  type        = string
}

variable "vpc_id" {
  description = "The ID of the VPC where the database will be deployed."
  type        = string
}

variable "vpc_cidr_block" {
  description = "The CIDR block of the VPC for the security group rule."
  type        = string
}

variable "private_subnet_ids" {
  description = "A list of private subnet IDs for the database."
  type        = list(string)
}

variable "db_instance_class" {
  description = "The instance type for the RDS database."
  type        = string
}

variable "db_name" {
  description = "The name of the database schema."
  type        = string
}

variable "db_username" {
  description = "The username for the RDS database."
  type        = string
}

variable "db_password" {
  description = "The password for the RDS database."
  type        = string
  sensitive   = true
}