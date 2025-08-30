"
Day15 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay15',
	#superclass : 'AoCBase',
	#instVars : [
		'map',
		'beacons',
		'sensors',
		'sensorsAndClosestBeaconDistances',
		'sensorsAndClosestBeacons'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay15 >> createMap: lines [

	map := Dictionary new.
	sensors := Set new.
	beacons := Set new.
	sensorsAndClosestBeaconDistances := Set new.
	sensorsAndClosestBeacons := Set new.

	lines do: [ :line |
			| nums sensor beacon dist sPos bPos |
			nums := line extractAllNumbers.
			sPos := nums first @ nums second.
			bPos := nums third @ nums fourth.

			sensor := AoCSensor at: sPos.
			sensors add: sensor.
			map at: sPos y at: sPos x put: sensor.

			beacon := AoCBeacon at: bPos.
			beacons add: beacon.
			map at: bPos y at: bPos x put: beacon.

			dist := sensor coords manhattanDistanceTo: beacon coords.
			sensorsAndClosestBeaconDistances add: sensor -> dist.
			sensorsAndClosestBeacons add: sensor -> beacon ]
]

{ #category : 'as yet unclassified' }
AoCDay15 >> part1: lines rowOfInterest: rowOfInterest [

	| minSensorCol maxSensorCol minBeaconCol maxBeaconCol minCol maxCol excludedCols |
	self createMap: lines.

	minSensorCol := sensors asOrderedCollection min: [ :sensor |
		                sensor coords x ].
	maxSensorCol := sensors asOrderedCollection max: [ :sensor |
		                sensor coords x ].
	minBeaconCol := beacons asOrderedCollection min: [ :sensor |
		                sensor coords x ].
	maxBeaconCol := beacons asOrderedCollection max: [ :sensor |
		                sensor coords x ].

	minCol := minSensorCol min: minBeaconCol - 5000000.
	maxCol := maxSensorCol max: maxBeaconCol + 5000000.

	excludedCols := Set new.

	sensorsAndClosestBeaconDistances do: [ :sendorAndDistance |
			| sensor dist |
			sensor := sendorAndDistance key.
			dist := sendorAndDistance value.

			minCol to: maxCol do: [ :col |
					(sensor coords manhattanDistanceTo: col @ rowOfInterest) <= dist
						ifTrue: [ excludedCols add: col ] ] ].

	(beacons select: [ :beacon | beacon coords y = rowOfInterest ]) do: [
		:beacon | excludedCols remove: beacon coords x ifAbsent: nil ].

	(sensors select: [ :sensor | sensor coords y = rowOfInterest ]) do: [
		:sensor | excludedCols remove: sensor coords x ifAbsent: nil ].

	^ excludedCols size
]
