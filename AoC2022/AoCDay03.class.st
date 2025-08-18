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

			   inter first isUppercase
				   ifTrue: [ inter first asInteger - 38 ]
				   ifFalse: [ inter first asInteger - 96 ] ]) sum
]
