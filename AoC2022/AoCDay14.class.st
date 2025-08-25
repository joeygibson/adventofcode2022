"
Day14 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay14',
	#superclass : 'AoCBase',
	#instVars : [
		'map'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay14 >> createMap: lines [

	| paths maxX maxY |
	maxX := 0.
	maxY := 0.

	paths := lines collect: [ :line |
			         (line substrings: ' -> ') collect: [ :point |
					         | coords |
					         coords := (point substrings: ',') collect: [ :p |
						                   p asInteger + 1 ].
					         maxX := maxX max: coords first.
					         maxY := maxY max: coords second.

					         coords first @ coords second ] ].

	map := CTNewArray2D
		       width: maxX + 10
		       height: maxY + 10.

	paths do: [ :path |
			path overlappingPairsDo: [ :a :b |
				(a pointsOnLineTo: b) do: [ :point | map at: point put: '#' ] ] ]
]

{ #category : 'testing' }
AoCDay14 >> isOpen: point [

	(map atColumn: point x atRow: point y) isNotNil
]

{ #category : 'testing' }
AoCDay14 >> isTheVoid: col [

	^ (map atColumn: col) isNil
]

{ #category : 'as yet unclassified' }
AoCDay14 >> part1: lines [

	| grains fallingIntoTheVoid |
	grains := 0.
	fallingIntoTheVoid := false.

	self createMap: lines.

	[ fallingIntoTheVoid ] whileFalse: [
			| cameToRest row col |
			cameToRest := false.
			row := 501.
			col := 2.

			[ cameToRest ] whileFalse: [
					(self isOpen: col @ row)
						ifTrue: [ row := row + 1 ]
						ifFalse: [
								| left right |
								left := col - 1.
								right := col + 1.

								(self isOpen: left @ row)
									ifTrue: [
											(self isTheVoid: left @ row)
												ifTrue: [ fallingIntoTheVoid := true ]
												ifFalse: [ col := left ] ]
									ifFalse: [
											(self isOpen: right @ row)
												ifTrue: [
														(self isTheVoid: right @ row)
															ifTrue: [ fallingIntoTheVoid := true ]
															ifFalse: [ col := right ] ]
												ifFalse: [ map at: col @ row put: 'o' ] ] ] ] ]
]
