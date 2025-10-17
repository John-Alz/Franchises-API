output "vpc_id" {
  description = "The ID of main VPC"
  value = aws_vpc.main.id
}

output "public_subnet_ids" {
  description = "A list of the public subnet IDs."
  value       = [aws_subnet.public_subnet.id, aws_subnet.public_subnet_2.id]
}


output "private_subnet_ids" {
  description = "A list of the private subnet IDs."
  value       = [aws_subnet.private_subnet.id, aws_subnet.private_subnet_2.id]
}

output "vpc_cidr_block" {
  description = "he CIDR block of the main VPC"
  value = aws_vpc.main.cidr_block
}

output "private_route_table_id" {
  description = "The ID of the private route table."
  value = aws_route_table.private_rt.id
}

output "public_route_table_id" {
  description = "The ID of the public route table."
  value = aws_route_table.public_rt.id
}