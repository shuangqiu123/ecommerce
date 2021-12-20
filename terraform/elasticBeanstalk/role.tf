resource "aws_iam_service_linked_role" "elasticbeanstalk" {
  aws_service_name = "elasticbeanstalk.amazonaws.com"
}

resource "aws_iam_instance_profile" "eb_ec2_profile" {
  name = "eb_ec2_profile"
  role = aws_iam_role.eb_ec2_role.name
}

resource "aws_iam_role" "eb_ec2_role" {
  name = "eb_ec2_role"
  path = "/"
  assume_role_policy = jsonencode({
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": "sts:AssumeRole",
            "Principal": {
               "Service": "ec2.amazonaws.com"
            },
            "Effect": "Allow"
        }
    ]
  })
}


resource "aws_iam_policy_attachment" "AWSElasticBeanstalkWebTier" {
  name       = "AWSElasticBeanstalkWebTier"
  roles      = [aws_iam_role.eb_ec2_role.name]
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWebTier"
}

resource "aws_iam_policy_attachment" "AWSElasticBeanstalkWorkerTier" {
  name       = "AWSElasticBeanstalkWorkerTier"
  roles      = [aws_iam_role.eb_ec2_role.name]
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWorkerTier"
}

resource "aws_iam_policy_attachment" "AWSElasticBeanstalkMulticontainerDocker" {
  name       = "AWSElasticBeanstalkMulticontainerDocker"
  roles      = [aws_iam_role.eb_ec2_role.name]
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkMulticontainerDocker"
}