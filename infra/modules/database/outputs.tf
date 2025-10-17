output "db_address" {
  description = "The connection address of the RDS instance."
  value       = aws_db_instance.franchise_db.address
}

output "db_name" {
  description = "The database name."
  value       = aws_db_instance.franchise_db.db_name
}

output "db_username" {
  description = "The database username."
  value       = aws_db_instance.franchise_db.username
}

output "db_password" {
  description = "The database password."
  value       = aws_db_instance.franchise_db.password
  sensitive   = true
}