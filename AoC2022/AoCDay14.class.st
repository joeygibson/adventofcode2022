"
Day14 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay14',
	#superclass : 'AoCBase',
	#instVars : [
		'map',
		'widthDiff',
		'heightDiff',
		'width',
		'height'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay14 >> adjustPoint: point [

	| col row |
	col := point x - widthDiff.
	row := point y.

	^ col @ row
]

{ #category : 'as yet unclassified' }
AoCDay14 >> createMap: lines [

	| paths maxX maxY minX |
	maxX := 0.
	maxY := 0.
	minX := Float infinity.

	paths := lines collect: [ :line |
			         (line substrings: ' -> ') collect: [ :point |
					         | coords |
					         coords := (point substrings: ',') collect: [ :p |
						                   p asInteger ].
					         maxX := maxX max: coords first.
					         maxY := maxY max: coords second.
					         minX := (minX min: coords first) asInteger.

					         coords first @ coords second ] ].

	width := maxX - minX + 1.
	height := maxY + 1.

	widthDiff := minX - 1.

	map := CTNewArray2D width: width height: height.

	paths do: [ :path |
			path overlappingPairsDo: [ :a :b |
					| aA aB |
					aA := self adjustPoint: a.
					aB := self adjustPoint: b.
					(aA pointsOnLineTo: aB) do: [ :point | map at: point put: '#' ] ] ]
]

{ #category : 'testing' }
AoCDay14 >> isOpen: point [

	^ (map at: point) isNil

	"|within nn|
	
	within := point isWithinWidth:  width height: height.
	nn := (map at: point) isNil.
	
	^ within and: nn."
]

{ #category : 'testing' }
AoCDay14 >> isTheVoid: col [

	^ col = 0 or: col > width
]

{ #category : 'as yet unclassified' }
AoCDay14 >> part1: lines [

	| grains |
	grains := 0.

	self createMap: lines.

	[ true ] whileTrue: [
			| cameToRest row col |
			cameToRest := false.
			col := 500 - widthDiff.
			row := 1.

			[
				[ cameToRest ] whileFalse: [ "check if this column is the void"
						(self isTheVoid: col @ row) ifTrue: [ ^ grains ].

						"now, check that the spot is open"
						(self isOpen: col @ row)
							ifTrue: [ row := row + 1 ]
							ifFalse: [
									| left right |
									left := col - 1.
									right := col + 1.

									"is the left column the void?"
									(self isTheVoid: left @ row) ifTrue: [ ^ grains ].

									"is the left column open"
									(self isOpen: col @ row)
										ifTrue: [ row := row + 1 ]
										ifFalse: [ "is the right column the void?"
												(self isTheVoid: right @ row) ifTrue: [ ^ grains ].

												"is the right column open"
												(self isOpen: right @ row)
													ifTrue: [ row := row + 1 ]
													ifFalse: [
															map at: col @ row put: 'o'.
															cameToRest := true.
															grains := grains + 1.
															Exception signal ] ] ] ] ]
				on: Exception
				do: [ "continue outer loop" ] ]
]
