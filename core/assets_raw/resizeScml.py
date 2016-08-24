import xml.etree.ElementTree as ET

tree = ET.parse('backup.scml')
root = tree.getroot()
	
for file in root.iter('file'):
	#print(file.tag, file.attrib)
	width = int(float(file.get('width')) * 0.5)
	height = int(float(file.get('height')) * 0.5)
	file.set('width', str(width))
	file.set('height', str(height))
	
tree.write('player.scml')
	