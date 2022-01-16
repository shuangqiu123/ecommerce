resource "aws_ecr_repository" "demostore" {
  name                 = "demostore"
}

resource "aws_ecr_lifecycle_policy" "foopolicy" {
  repository = aws_ecr_repository.demostore.name

  policy = <<EOF
{
    "rules": [
        {
            "rulePriority": 1,
            "selection": {
                "tagStatus": "any",
                "countType": "imageCountMoreThan",
                "countNumber": 3
            },
            "action": {
                "type": "expire"
            }
        }
    ]
}
EOF
}