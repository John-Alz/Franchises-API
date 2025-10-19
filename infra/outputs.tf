# outputs.tf

output "ecr_repository_url" {
  description = "La URL del repositorio ECR para la API."
  value       = aws_ecr_repository.franchise_api_repo.repository_url
}


output "db_endpoint" {
  description = "The connection endpoint for the RDS database instance."
  value       = module.database.db_address
}


output "alb_dns_name" {
  description = "The public DNS name of the Application Load Balancer."
  value       = aws_lb.main.dns_name
}

output "demo_bastion_id" {
  value       = aws_instance.demo_ssm_bastion.id
  description = "El ID de la instancia EC2 'puente' para usar con SSM."
}