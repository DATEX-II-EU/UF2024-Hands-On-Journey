#!/usr/bin/env bash

cd src/main/resources

echo "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
echo "<jaxb:bindings version=\"3.0\" xmlns:jaxb=\"https://jakarta.ee/xml/ns/jaxb\""
echo "    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\""
echo "    xmlns:annox=\"http://jvnet.org/basicjaxb/xjc/annox\""
echo "    jaxb:extensionBindingPrefixes=\"xjc\">"

for b in `grep -oP 'complexType name="\K[^"]+' *.xsd`; do
  f=${b%%:*}
  t=${b##*:}
  if [[ "$t" == "_"* ]]; then
    tn=Underscore${t##_}
    echo "  <jaxb:bindings schemaLocation=\"$f\" node=\"//xs:complexType[@name='$t']\">"
    echo "    <jaxb:class name=\"$tn\"/>"
    echo "  </jaxb:bindings>"
  elif [[ "$t" == *"Publication" ]]; then
    echo "  <jaxb:bindings schemaLocation=\"$f\" node=\"//xs:complexType[@name='$t']\">"
    echo "    <annox:annotate>"
    echo "      <annox:annotate annox:class=\"jakarta.xml.bind.annotation.XmlRootElement\"/>"
    echo "    </annox:annotate>"
    echo "  </jaxb:bindings>"
  elif [[ "$t" == *"MessageContainer" ]]; then
    echo "  <jaxb:bindings schemaLocation=\"$f\" node=\"//xs:complexType[@name='$t']\">"
    echo "    <annox:annotate>"
    echo "      <annox:annotate annox:class=\"jakarta.xml.bind.annotation.XmlRootElement\"/>"
    echo "    </annox:annotate>"
    echo "  </jaxb:bindings>"
  fi
done

echo "</jaxb:bindings>"
