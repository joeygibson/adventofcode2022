"
Day07 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay07',
	#superclass : 'AoCBase',
	#instVars : [
		'drive',
		'cwd'
	],
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'parsing' }
AoCDay07 >> parse: lines [

	| dirStack |
	drive := Dictionary new.
	dirStack := OrderedCollection new.

	lines do: [ :line |
			| chunks |
			chunks := line substrings.

			(line beginsWith: '$ cd') ifTrue: [
					(line endsWith: '/')
						ifTrue: [
								dirStack
									removeAll;
									add: drive.
								cwd := drive ]
						ifFalse: [
								chunks third = '..'
									ifTrue: [
											dirStack removeLast.
											cwd := dirStack last ]
									ifFalse: [
											cwd := cwd at: chunks third ifAbsentPut: [ Dictionary new ].
											dirStack add: cwd ] ] ].

			(line beginsWith: 'dir ') ifTrue: [
				cwd at: chunks second ifAbsentPut: [ Dictionary new ] ].

			chunks first isAllDigits ifTrue: [
				cwd at: chunks second put: chunks first asInteger ] ].
	dirStack := OrderedCollection new.

	lines do: [ :line |
			| chunks |
			chunks := line substrings.

			(line beginsWith: '$ cd') ifTrue: [
					(line endsWith: '/')
						ifTrue: [
								dirStack
									removeAll;
									add: drive.
								cwd := drive ]
						ifFalse: [
								chunks third = '..'
									ifTrue: [
											dirStack removeLast.
											cwd := dirStack last ]
									ifFalse: [
											cwd := cwd at: chunks third ifAbsentPut: [ Dictionary new ].
											dirStack add: cwd ] ] ].

			(line beginsWith: 'dir ') ifTrue: [
				cwd at: chunks second ifAbsentPut: [ Dictionary new ] ].

			chunks first isAllDigits ifTrue: [
				cwd at: chunks second put: chunks first asInteger ] ]
]

{ #category : 'as yet unclassified' }
AoCDay07 >> part1: lines [

	| dirs interesting |
	self parse: lines.

	dirs := self walkDir: drive.

	interesting := dirs select: [ :item | item second <= 100000 ].

	^ interesting sum: [ :item | item second ]
]

{ #category : 'as yet unclassified' }
AoCDay07 >> part2: lines [

	| dirs biggest root targetSize foo |
	self parse: lines.

	dirs := self walkDir: drive.

	root := (dirs sort: [ :a :b | b second < a second ]) first.
	targetSize := 70000000 - root second.
	targetSize := 30000000 - targetSize.

	foo := (dirs select: [ :item | item second >= targetSize ]) sort: [
		       :a
		       :b | b second <= a second ].

	biggest := foo second.

	^ biggest second
]

{ #category : 'as yet unclassified' }
AoCDay07 >> walkDir: dir [

	| res total |
	res := OrderedCollection new.
	total := 0.

	dir keysAndValuesDo: [ :name :value |
			value isDictionary
				ifTrue: [ res addAll: (self walkDir: value) ]
				ifFalse: [ total := total + value ] ].

	res do: [ :item | total := total + item second ].

	"dir = drive ifFalse: [ res removeAll ]."

	res add: (OrderedCollection with: dir with: total).

	^ res
]
