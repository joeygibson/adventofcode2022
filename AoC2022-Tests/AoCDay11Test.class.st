Class {
	#name : 'AoCDay11Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay11Test >> setUp [
	super setUp.
	self day: '11'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay11 new.
]

{ #category : 'tests' }
AoCDay11Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 117640.
]

{ #category : 'tests' }
AoCDay11Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 10605.
]

{ #category : 'tests' }
AoCDay11Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 30616425600.
]

{ #category : 'tests' }
AoCDay11Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.
	
	res := inst part2: lines.

	self assert: res equals: 2713310158.
]
