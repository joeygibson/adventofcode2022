Class {
	#name : 'Monkey',
	#superclass : 'Object',
	#instVars : [
		'items',
		'inspectionCount',
		'trueMonkey',
		'falseMonkey',
		'operation',
		'divisor'
	],
	#category : 'AoC2022-Day11',
	#package : 'AoC2022-Day11'
}

{ #category : 'instance creation' }
Monkey class >> from: lines [

	| items chunks operation divisor |
	items := '\d+' asRegex
		         matchesIn: lines second
		         collect: [ :c | c asInteger ].

	chunks := lines third substrings reversed.

	operation := chunks second = '+'
		             ifTrue: [
				             chunks first = 'old'
					             ifTrue: [ [ :a | a + a ] ]
					             ifFalse: [ [ :a | a + chunks first asInteger ] ] ]
		             ifFalse: [
				             chunks first = 'old'
					             ifTrue: [ [ :a | a * a ] ]
					             ifFalse: [ [ :a | a * chunks first asInteger ] ] ].

	divisor := lines fourth substrings last asInteger.

	^ self new
		  items: items;
		  operation: operation;
		  divisor: divisor
]

{ #category : 'accessing' }
Monkey >> divisor [

	^ divisor
]

{ #category : 'accessing' }
Monkey >> divisor: anObject [

	divisor := anObject
]

{ #category : 'accessing' }
Monkey >> falseMonkey [

	^ falseMonkey
]

{ #category : 'accessing' }
Monkey >> falseMonkey: anObject [

	falseMonkey := anObject
]

{ #category : 'initialization' }
Monkey >> initialize [ 
	inspectionCount := 0.
	items := OrderedCollection new.
]

{ #category : 'accessing' }
Monkey >> inspectionCount [

	^ inspectionCount
]

{ #category : 'accessing' }
Monkey >> inspectionCount: anObject [

	inspectionCount := anObject
]

{ #category : 'accessing' }
Monkey >> items [

	^ items
]

{ #category : 'accessing' }
Monkey >> items: anObject [

	items := anObject
]

{ #category : 'accessing' }
Monkey >> operation [

	^ operation
]

{ #category : 'accessing' }
Monkey >> operation: anObject [

	operation := anObject
]

{ #category : 'as yet unclassified' }
Monkey >> receiveItem: item [

	items add: item.
]

{ #category : 'as yet unclassified' }
Monkey >> takeTurn [

	self takeTurn: 3
]

{ #category : 'as yet unclassified' }
Monkey >> takeTurn: worryReducer [

	items copy do: [ :item |
			| newItem |
			inspectionCount := inspectionCount + 1.

			newItem := operation value: item.
			worryReducer = 3
				ifTrue: [ newItem := newItem // 3 ]
				ifFalse: [ newItem := newItem \\ worryReducer ].

			newItem \\ divisor = 0
				ifTrue: [ trueMonkey receiveItem: newItem ]
				ifFalse: [ falseMonkey receiveItem: newItem ] ].

	items removeAll
]

{ #category : 'accessing' }
Monkey >> trueMonkey [

	^ trueMonkey
]

{ #category : 'accessing' }
Monkey >> trueMonkey: anObject [

	trueMonkey := anObject
]
