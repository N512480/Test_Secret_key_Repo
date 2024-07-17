cd Test_Secret_key_Repo/src/main/resources

echo ${"aws_host= "} ${{ secrets.EMAIL_SECRET_PASSPHRASE }} >> testSecProp.properties