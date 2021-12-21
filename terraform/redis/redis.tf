resource "aws_elasticache_cluster" "redis" {
  cluster_id           = "redis"
  engine               = "redis"
  node_type            = "cache.t3.micro"
  num_cache_nodes      = 1
  parameter_group_name = aws_elasticache_parameter_group.default.id
  port                 = 6379
}

resource "aws_elasticache_parameter_group" "default" {
  name   = "cache-params"
  family = "redis6.x"
}