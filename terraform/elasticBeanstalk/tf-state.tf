terraform {
  backend "s3" {
    bucket = "sq-aws-terraform-state"
    key    = "ecommerce-eb.json"
  }
}