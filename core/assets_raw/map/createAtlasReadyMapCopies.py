import xml.etree.ElementTree as ET
import sys
from shutil import copyfile
import math

def create(parseMe, multiplier):
	tree = ET.parse(parseMe)
	root = tree.getroot()
	
	# add atlas property for AtlasTmxMapLoader
	properties = ET.Element('properties')
	property = ET.Element('property')
	property.attrib['name'] = 'atlas'
	property.attrib['value'] = 'map.pack.atlas'
	properties.insert(0, property)
	prettify(properties, 1)
	root.insert(0, properties)
	
	# change width and height in root element
	tilewidth = int(math.ceil(float(root.get('tilewidth')) * multiplier));
	tileheight = int(math.ceil(float(root.get('tileheight')) * multiplier));
	root.set('tilewidth', str(tilewidth))
	root.set('tileheight', str(tileheight))
	# change width and height for all image elements in the tileset
	for image in root.iter('image'):
		image.set('width', str(tilewidth))
		image.set('height', str(tileheight))

	tree.write(parseMe, encoding='utf-8', xml_declaration=True)

def prettify(elem, level=0):
  i = "\n" + level*" "
  if len(elem):
    if not elem.text or not elem.text.strip():
      elem.text = i + " "
    if not elem.tail or not elem.tail.strip():
      elem.tail = i
    for elem in elem:
      prettify(elem, level+1)
    if not elem.tail or not elem.tail.strip():
      elem.tail = i
  else:
    if level and (not elem.tail or not elem.tail.strip()):
      elem.tail = i
	
if len(sys.argv) < 2:
	print "Provide the name of the file please!"
	sys.exit(0)
	
print "Parsing..."

fIn = sys.argv[1]
fOuts = ["xl.tmx", "l.tmx", "m.tmx", "s.tmx"]

copyfile(fIn, fOuts[0])
copyfile(fIn, fOuts[1])
copyfile(fIn, fOuts[2])
copyfile(fIn, fOuts[3])

create(fOuts[0], 1)
create(fOuts[1], 0.66666666666)
create(fOuts[2], 0.5)
create(fOuts[3], 0.33333333333)




