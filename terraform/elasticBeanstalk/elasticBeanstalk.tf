resource "aws_elastic_beanstalk_application" "demostore" {
  name        = "demostore"
  description = "eb application for demostore"

  appversion_lifecycle {
    service_role          = aws_iam_service_linked_role.elasticbeanstalk.arn
    max_count             = 128
    delete_source_from_s3 = true
  }
}

resource "aws_elastic_beanstalk_environment" "demostore" {
  depends_on = [module.vpc]

  name                = "demostore"
  application         = aws_elastic_beanstalk_application.demostore.name
  solution_stack_name = "64bit Amazon Linux 2 v3.4.9 running Docker"
 
  // vpc
  setting {
    namespace = "aws:ec2:vpc"
    name      = "VPCId"
    value     = module.vpc.vpc_id
  }

  setting {
    namespace = "aws:ec2:vpc"
    name      = "Subnets"
    value     = "${module.vpc.private_subnets[0]},${module.vpc.private_subnets[1]}"
  }
  
  setting {
    namespace = "aws:ec2:vpc"
    name = "ELBSubnets"
    value = "${module.vpc.public_subnets[0]},${module.vpc.public_subnets[1]}"
  }

  // asg
  setting {
    namespace = "aws:autoscaling:asg"
    name = "MinSize"
    value = 1
  }

  setting {
    namespace = "aws:autoscaling:asg"
    name = "MaxSize"
    value = 1
  }

  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name = "IamInstanceProfile"
    value = aws_iam_instance_profile.eb_ec2_profile.arn
  }

  // ec2 instance
  setting {
    namespace = "aws:ec2:instances"
    name = "InstanceTypes"
    value = "t2.micro"
  }

  // logs
  setting {
    namespace = "aws:elasticbeanstalk:cloudwatch:logs"
    name = "StreamLogs"
    value = true
  }

  // environment
  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name = "ServiceRole"
    value = aws_iam_service_linked_role.elasticbeanstalk.arn
  }

  setting {
    namespace = "aws:elasticbeanstalk:environment"
    name = "LoadBalancerType"
    value = "application"
  }

  // application load balancer
  setting {
    namespace = "aws:elbv2:listener:default"
    name = "ListenerEnabled"
    value = false
  }
  setting {
    namespace = "aws:elbv2:listener:443"
    name = "Protocol"
    value = "HTTPS"
  }

  setting {
    namespace = "aws:elbv2:listener:443"
    name = "SSLCertificateArns"
    value = var.BACKEND_CERT_ARN
  }

  setting {
    namespace = "aws:elbv2:listener:443"
    name = "SSLPolicy"
    value = "ELBSecurityPolicy-TLS-1-2-Ext-2018-06"
  }

  // environment variables

}