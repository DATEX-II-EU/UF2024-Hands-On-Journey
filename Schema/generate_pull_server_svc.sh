#!/bin/bash
wsdlpath=""
exschemapath=""
schemapath=""
outputDir="../Demo DotNetCore/DemoSoapServer/Model"
outputFile="SnapshotPullSvc.cs"
# Print the paths for debugging
echo "Output Directory: $outputDir"
echo "Output File: $outputFile"

# Check if the file exists before attempting to remove it
if [ -f "$outputDir/$outputFile" ]; then
    echo "File exists. Removing $outputDir/$outputFile"
    rm -f "$outputDir/$outputFile"
else
    echo "File does not exist. No need to remove."
fi

"dotnet-svcutil" \
$wsdlpath"SnapshotPull.wsdl" \
    -v Debug \
    --sync \
    --noStdLib \
    --serializer XmlSerializer \
    --outputFile "$outputFile" \
    --outputDir "$outputDir" \
    --namespace "*,DemoSoapServer" \
    "$exschemapath""DATEXII_3_MessageContainer.xsd" \
    "$exschemapath""DATEXII_3_ExchangeInformation.xsd" \
    "$schemapath""DATEXII_3_Common.xsd" \
    "$schemapath""DATEXII_3_D2Payload.xsd" \
    "$schemapath""DATEXII_3_LocationReferencing.xsd" \
    "$schemapath""DATEXII_3_RoadTrafficData.xsd" \
    "$schemapath""DATEXII_3_Situation.xsd"
