"
Day05 of Advent of Code 2022.
"
Class {
	#name : 'AoCDay05',
	#superclass : 'AoCBase',
	#category : 'AoC2022',
	#package : 'AoC2022'
}

{ #category : 'as yet unclassified' }
AoCDay05 >> buildStacks: lines [

	| stacks stackNumbers stackLayout |
	stacks := Dictionary new.

	stackNumbers := (lines last findTokens: ' ') collect: [ :c | c asInteger ].
	stackLayout := lines reversed allButFirst.

	stackLayout do: [ :row |
			stackNumbers doWithIndex: [ :stackNumber :index |
					| box stack |
					stack := stacks
						         at: stackNumber
						         ifAbsentPut: [ OrderedCollection new ].

					box := row at: index - 1 * 4 + 2.

					box ~= Character space ifTrue: [
							stacks at: stackNumber update: [ :s |
									s
										add: box;
										yourself ] ] ] ].

	^ stacks
]

{ #category : 'as yet unclassified' }
AoCDay05 >> part1: lines [

	| stacks sections |
	sections := lines splitOn: [ :line | line isEmpty ].

	stacks := self buildStacks: sections first.

	sections second do: [ :line |
			| nums from to count |
			nums := '\d+' asRegex matchesIn: line collect: [ :c | c asInteger ].
			from := stacks at: nums second.
			to := stacks at: nums third.
			count := nums first.

			count timesRepeat: [
					| box |
					box := from removeLast.
					to add: box ] ].

	^ (stacks keys sorted collect: [ :stackNum |
		   (stacks at: stackNum) removeLast ]) joinUsing: ''
]

{ #category : 'as yet unclassified' }
AoCDay05 >> part2: lines [

	| stacks sections |
	sections := lines splitOn: [ :line | line isEmpty ].

	stacks := self buildStacks: sections first.

	sections second do: [ :line |
			| nums from to count boxes |
			nums := '\d+' asRegex matchesIn: line collect: [ :c | c asInteger ].
			from := stacks at: nums second.
			to := stacks at: nums third.
			count := nums first.

			boxes := from removeLast: count.
			to addAll: boxes ].

	^ (stacks keys sorted collect: [ :stackNum |
			   | stack |
			   stack := stacks at: stackNum.
			   stack isEmpty ifFalse: [ stack removeLast ] ]) joinUsing: ''
]
