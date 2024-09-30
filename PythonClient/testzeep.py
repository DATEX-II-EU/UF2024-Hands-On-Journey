import zeep
from requests import Session
from zeep.transports import Transport

##wsdl = 'http://datex.vegagerdin.is/situationpublication3_1/SituationService?wsdl'
## To call external URL the port visiabillity has to be public
## wsdl ='https://scaling-spoon-jgjx7w6jrj92q45j-5001.app.github.dev/SituationService'
wsdl ='https://localhost:5001/SituationService'

# Create a session and disable SSL verification
session = Session()
session.verify = False  # Disable SSL verification

# Create a transport with the session
transport = Transport(session=session)

client = zeep.Client(wsdl=wsdl,transport=transport)
result = client.service.pullSnapshotData()
print(result)
print("Number of situations:" + str(len(result.payload[0].situation)))
payload = result.payload[0]
print("PublicationTime:" + str(payload.publicationTime))
situation = result.payload[0].situation[0]
situationRecord = situation.situationRecord[0]
print("SituationRecord.ID:" + situationRecord.id)