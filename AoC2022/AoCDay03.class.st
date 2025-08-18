"
Day03 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay03',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay03 >> part1: lines [

	^ (lines collect: [ :line |
			   | firstHalf secondHalf inter |
			   firstHalf := line takeFirst: line size // 2.
			   secondHalf := line allButFirst: line size // 2.

			   inter := firstHalf intersection: secondHalf.

			   self prioritize: inter ]) sum
]

{ #category : 'as yet unclassified' }
AoCDay03 >> part2: lines [

	^ ((lines groupsOf: 3) collect: [ :group |
			   | inter |
			   inter := group first intersection:
				            (group second intersection: group third).
			   self prioritize: inter ]) sum
]

{ #category : 'as yet unclassified' }
AoCDay03 >> prioritize: aChar [

	^ aChar first isUppercase
		  ifTrue: [ aChar first asInteger - 38 ]
		  ifFalse: [ aChar first asInteger - 96 ]
]
